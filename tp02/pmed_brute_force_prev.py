import os
import numpy as np
from scipy.special import gammaln
from sklearn.linear_model import LinearRegression
import matplotlib.pyplot as plt

COMPUTED_PMED_INSTANCES = [1, 6, 11, 16]
PMED_NUMBER_OF_INSTANCES = 40  # [1..40]
PMED_INSTANCES_DIR = "./pmed_instances"
RESULT_DIR = "./result/brute-force"
PREDICTIONS_DIR = os.path.join(RESULT_DIR, "predictions")

def model_data(instances_dir, result_dir):
    train = []
    prev = []

    for i in range(1, PMED_NUMBER_OF_INSTANCES + 1):
        instance_filename = f"pmed{i}.txt"
        instance_filepath = os.path.join(instances_dir, instance_filename)

        if not os.path.exists(instance_filepath): continue

        try:
            with open(instance_filepath, 'r') as file:
                first_line = file.readline().strip()
                n, m, k = map(int, first_line.split())
                instance_data = {'N': n, 'M': m, 'K': k, 'instance': i}
        except ValueError:
            print(f"Error {instance_filename}: line '{first_line}'")
            continue

        result_filename = f"pmed{i}_result.txt"
        result_filepath = os.path.join(result_dir, result_filename)

        if i in COMPUTED_PMED_INSTANCES and os.path.exists(result_filepath):
            try:
                with open(result_filepath, 'r') as result_file:
                    for line in result_file:
                        if line.startswith("Execution time:"):
                            time = float(line.split(":")[1].strip().replace("s", ""))
                            instance_data['T'] = time
                            break
                train.append(instance_data)
            except (ValueError, IndexError):
                print(f"Erro ao ler o tempo em {result_filename}")
        else:
            prev.append(instance_data)

    return train, prev
    
def log_CNK(N, K):
    return gammaln(N + 1) - gammaln(K + 1) - gammaln(N - K + 1)

def compute_log_complexity(N, K):
    log_C = log_CNK(N, K)
    log_K = np.log(K)
    log_N = np.log(N)
    return log_C + log_K + log_N




train_data, prev_data = model_data(PMED_INSTANCES_DIR, RESULT_DIR)

N_values = np.array([d['N'] for d in train_data])
K_values = np.array([d['K'] for d in train_data])
T_observed = np.array([d['T'] for d in train_data])

log_complexity = compute_log_complexity(N_values, K_values)
log_T = np.log(T_observed)

X = log_complexity.reshape(-1, 1)  
y = log_T 

model = LinearRegression()
model.fit(X, y)

intercept = model.intercept_
slope = model.coef_[0]
print(f"Model equation: log(T) = {intercept:.4f} + {slope:.4f} * log(O( C(N,K) * K * N))")

r_squared = model.score(X, y)
print(f"R²: {r_squared:.4f}")

def predict_time(N_new, K_new):
    log_comp_new = compute_log_complexity(N_new, K_new)
    log_T_pred = model.predict(log_comp_new.reshape(-1, 1))
    T_pred = np.exp(log_T_pred)
    return T_pred

N_new = np.array([d['N'] for d in prev_data])
K_new = np.array([d['K'] for d in prev_data])
T_predicted = predict_time(N_new, K_new)


os.makedirs(PREDICTIONS_DIR, exist_ok=True)

for instance, predicted_time in zip(prev_data, T_predicted):
    instance_id = instance['instance']
    prediction_filename = f"pmed{instance_id}_result_prev.txt"
    prediction_filepath = os.path.join(PREDICTIONS_DIR, prediction_filename)
    
    with open(prediction_filepath, 'w') as prediction_file:
        prediction_file.write(f"Execution time: {predicted_time:.4f}s\n")


