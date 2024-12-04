import os
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from math import comb

def read_pmed_instances(directory, ninstances=40):
    vertices = []
    clusters = []
    instances = []
    for i in range(1, ninstances + 1):
        filename = f"pmed{i}.txt"
        filepath = os.path.join(directory, filename)
        if os.path.exists(filepath):
            with open(filepath, 'r') as file:
                line = file.readline().strip()
                try:
                    n, _, k = map(int, line.split())
                    vertices.append(n)
                    clusters.append(k)
                    instances.append(i)
                except ValueError:
                    print(f"Error processing {filename}: {line}")
    return instances, vertices, clusters

def read_results(dir, num_results=40):
    results = []
    for i in range(1, num_results + 1):
        filename = f"pmed{i}_result.txt"
        filepath = os.path.join(dir, filename)
        if not os.path.exists(filepath):
            continue
        with open(filepath, 'r') as file:
            radius, execution_time, max_memory_used = None, None, None
            for line in file:
                line = line.strip()
                if line.startswith("Radius:"):
                    try:
                        radius = int(line.split(": ")[1])
                    except ValueError:
                        radius = None
                elif line.startswith("Execution time:"):
                    try:
                        execution_time = float(line.split(": ")[1].replace("s", ""))
                    except ValueError:
                        execution_time = None
                elif line.startswith("Max memory used:"):
                    try:
                        max_memory_used = int(line.split(": ")[1].replace(" KB", ""))
                    except ValueError:
                        max_memory_used = None
            results.append({
                "instance": i,
                "radius": radius,
                "execution_time": execution_time,
                "max_memory_used": max_memory_used
            })
    return results

def plot_vertices_and_clusters(instances, vertices, clusters):
    x = np.arange(len(instances))
    width = 0.35
    plt.figure(figsize=(14, 8))
    plt.bar(x - width / 2, vertices, width, label="Vertices", color="red")
    plt.bar(x + width / 2, clusters, width, label="Clusters", color="green")
    plt.title("Number of Vertices and Clusters per Instance")
    plt.xlabel("Instances")
    plt.ylabel("Quantity")
    plt.xticks(x, [f"{i}" for i in instances], rotation=45)
    plt.legend(loc="upper left")
    plt.grid(axis='y', linestyle='--', alpha=0.6)
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/vertices_clusters.png")
    plt.show()

def plot_combinations_growth(vertices, clusters):
    combinations = [comb(n, k) if k <= n else 0 for n, k in zip(vertices, clusters)]
    plt.figure(figsize=(14, 8))
    plt.plot(range(1, len(combinations) + 1), combinations, label=r"$C(n, k) = \frac{n!}{k!(n-k)!}$", marker='o')
    plt.title("Growth of Number of Combinations")
    plt.xlabel("Instances")
    plt.ylabel("Number of Combinations")
    plt.yscale("log")
    plt.xticks(range(1, len(combinations) + 1), rotation=45)
    plt.legend(loc="upper left")
    plt.grid(axis='y', linestyle='--', alpha=0.6)
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/combinations_growth.png")
    plt.show()

def plot_brute_force_execution_time(brute_force, brute_force_pred):
    brute_force_dict = {result['instance']: result['execution_time'] for result in brute_force}
    brute_force_pred_dict = {result['instance']: result['execution_time'] for result in brute_force_pred}
    all_instances = sorted(set(brute_force_dict.keys()) | set(brute_force_pred_dict.keys()))
    combined_times = []
    for instance in all_instances:
        if instance in brute_force_dict:
            combined_times.append(brute_force_dict[instance])
        elif instance in brute_force_pred_dict:
            combined_times.append(brute_force_pred_dict[instance])
        else:
            combined_times.append(None)
    combined_times_in_days = [time / (60 * 60 * 24) if time is not None else None for time in combined_times]
    execution_times_in_days = [brute_force_dict.get(instance, None) / (60 * 60 * 24) if instance in brute_force_dict else None for instance in all_instances]
    predicted_times_in_days = [brute_force_pred_dict.get(instance, None) / (60 * 60 * 24) if instance in brute_force_pred_dict else None for instance in all_instances]
    plt.figure(figsize=(12, 6))
    plt.plot(
        all_instances,
        combined_times_in_days,
        label="Combined (Line)",
        color="gray",
        linestyle="-",
        zorder=1,
    )
    plt.scatter(
        all_instances,
        execution_times_in_days,
        label="Brute Force (Computed)",
        color="blue",
        zorder=2,
    )
    plt.scatter(
        all_instances,
        predicted_times_in_days,
        label="Brute Force (Predicted)",
        color="purple",
        zorder=3,
    )
    plt.yscale('log')
    plt.title("Execution Time Comparison (Log Scale in Days)")
    plt.xlabel("Instance")
    plt.ylabel("Execution Time (days, log scale)")
    plt.legend()
    plt.grid(True, which="both", linestyle="--", linewidth=0.5)
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/brute_force_execution_time.png")
    plt.show()

def plot_computed_brute_force_table(brute_force_computed):
    computed_data = [
        {"Instance": result["instance"], "Execution Time (minutes)": int(result["execution_time"] / 60)}
        for result in brute_force_computed if result["execution_time"] is not None
    ]
    df = pd.DataFrame(computed_data)
    if df.empty:
        return
    _, ax = plt.subplots(figsize=(10, len(df) * 0.5))
    ax.axis('tight')
    ax.axis('off')
    table = ax.table(
        cellText=df.values,
        colLabels=df.columns,
        cellLoc='center',
        loc='center'
    )
    table.auto_set_font_size(False)
    table.set_fontsize(10)
    if not df.empty:
        table.auto_set_column_width(col=list(range(len(df.columns))))
    plt.title("Computed Brute Force Results")
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/computed_brute_force_table.png")
    plt.show()

def compare_memory_usage_same_instances(brute_force, greedy):
    brute_force_memory = {
        result["instance"]: result["max_memory_used"] for result in brute_force if result["max_memory_used"] is not None
    }
    greedy_memory = {
        result["instance"]: result["max_memory_used"] for result in greedy if result["max_memory_used"] is not None
    }
    common_instances = sorted(set(brute_force_memory.keys()).intersection(greedy_memory.keys()))
    brute_force_values = [brute_force_memory[instance] for instance in common_instances]
    greedy_values = [greedy_memory[instance] for instance in common_instances]
    brute_force_avg = np.mean(brute_force_values)
    greedy_avg = np.mean(greedy_values)
    plt.figure(figsize=(12, 6))
    plt.plot(
        common_instances,
        brute_force_values,
        label="Brute Force",
        color="blue",
        marker="o",
        linestyle="-",
    )
    plt.plot(
        common_instances,
        greedy_values,
        label="Greedy",
        color="orange",
        marker="x",
        linestyle="--",
    )
    plt.axhline(y=brute_force_avg, color="blue", linestyle=":", label=f"Brute Force Avg: {brute_force_avg:.2f} KB")
    plt.axhline(y=greedy_avg, color="orange", linestyle=":", label=f"Greedy Avg: {greedy_avg:.2f} KB")
    plt.title("Comparison of Maximum Memory Usage (Same Instances)")
    plt.xlabel("Instances")
    plt.ylabel("Max Memory Used (KB)")
    plt.legend()
    plt.grid(True, linestyle="--", alpha=0.6)
    plt.xticks(common_instances)
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/memory_usage_comparison.png")
    plt.show()

def plot_greedy_execution_time(greedy):
    greedy_dict = {result['instance']: result['execution_time'] for result in greedy if result['execution_time'] is not None}
    instances = sorted(greedy_dict.keys())
    values = [greedy_dict[i] for i in instances]
    plt.figure(figsize=(12, 6))
    plt.plot(
        instances,
        values,
        color="orange",
        marker="o",
        linestyle="-"
    )
    plt.title("Greedy Execution Time")
    plt.xlabel("Instances")
    plt.ylabel("Execution Time (s)")
    plt.grid(True, linestyle="--", alpha=0.6)
    plt.xticks(instances)
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/greedy_execution_time.png")
    plt.show()

def read_correct_solutions(filepath):
    data = []
    with open(filepath, 'r') as file:
        for line in file:
            line = line.strip()
            parts = line.split()
            if len(parts) == 4:
                inst = int(parts[0])
                # N = int(parts[1])  # We do not necessarily need these
                # K = int(parts[2])
                correct_radius = int(parts[3])
                data.append({"instance": inst, "correct_radius": correct_radius})
    return pd.DataFrame(data)

def plot_radius_comparison_table(correct_solutions, greedy):
    greedy_dict = {res["instance"]: res["radius"] for res in greedy if res["radius"] is not None}
    merged = correct_solutions.copy()
    merged["greedy_radius"] = merged["instance"].apply(lambda x: greedy_dict.get(x, None))
    df = merged[["instance", "correct_radius", "greedy_radius"]]

    # Create a table figure
    if df.empty:
        return
    fig, ax = plt.subplots(figsize=(10, len(df)*0.5))
    ax.axis('tight')
    ax.axis('off')
    table = ax.table(
        cellText=df.values,
        colLabels=df.columns,
        cellLoc='center',
        loc='center'
    )
    table.auto_set_font_size(False)
    table.set_fontsize(10)
    table.auto_set_column_width(col=list(range(len(df.columns))))

    plt.title("Radius Comparison (Correct vs Greedy)")
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/radius_comparison_table.png")
    plt.show()

def plot_radius_error(correct_solutions, greedy):
    greedy_dict = {res["instance"]: res["radius"] for res in greedy if res["radius"] is not None}
    merged = correct_solutions.copy()
    merged["greedy_radius"] = merged["instance"].apply(lambda x: greedy_dict.get(x, None))
    merged = merged.dropna(subset=["greedy_radius"])
    merged["error"] = (merged["greedy_radius"] - merged["correct_radius"]).abs()

    instances = merged["instance"].values
    errors = merged["error"].values

    plt.figure(figsize=(12, 6))
    plt.bar(instances, errors)
    plt.title("Absolute Error in Radius (Greedy vs Correct)")
    plt.xlabel("Instances")
    plt.ylabel("Absolute Error")
    plt.xticks(instances, rotation=45)
    plt.grid(axis='y', linestyle='--', alpha=0.6)
    plt.tight_layout()
    os.makedirs("./result", exist_ok=True)
    plt.savefig("./result/radius_error.png")
    plt.show()

instances, vertices, clusters = read_pmed_instances("./pmed_instances")
greedy = read_results("./result/greedy")
brute_force = read_results("./result/brute-force")
brute_force_pred = read_results("./result/brute-force/predictions")

plot_vertices_and_clusters(instances, vertices, clusters)
plot_combinations_growth(vertices, clusters)
plot_brute_force_execution_time(brute_force, brute_force_pred)
plot_computed_brute_force_table(brute_force)
compare_memory_usage_same_instances(brute_force, greedy)
plot_greedy_execution_time(greedy)

correct_solutions = read_correct_solutions("./correct_pmed_solutions.txt")
plot_radius_comparison_table(correct_solutions, greedy)
plot_radius_error(correct_solutions, greedy)
