#!/bin/bash

# Nome da pasta onde os arquivos serão armazenados
OUTPUT_DIR="./pmed_instances"

# URL base onde os arquivos estão hospedados
BASE_URL="http://people.brunel.ac.uk/~mastjjb/jeb/orlib/files"

# Função para exibir a mensagem de uso
function usage() {
    echo "Uso: $0 [--clean]"
    echo "Sem argumentos: Realiza o download das 40 instâncias ($OUTPUT_DIR)."
    echo "--clean: Remove todos os arquivos baixados e o diretório em ($OUTPUT_DIR)."
    exit 1
}

# Função para limpar os arquivos baixados
function clean_files() {
    if [[ -d "$OUTPUT_DIR" ]]; then
        echo "Removendo arquivos e pasta $OUTPUT_DIR..."
        rm -rf "$OUTPUT_DIR"
        echo "Arquivos removidos com sucesso!"
    else
        echo "Nenhum arquivo encontrado para remover."
    fi
    exit 0
}

# Verificar se foi passada a flag --clean
if [[ "$1" == "--clean" ]]; then
    clean_files
elif [[ "$1" == "--help" || "$1" == "-h" ]]; then
    usage
elif [[ -n "$1" ]]; then
    echo "Flag inválida: $1"
    usage
fi

# Criar o diretório de saída, se não existir
mkdir -p "$OUTPUT_DIR"

echo "Iniciando o download das 40 instâncias para a pasta: $OUTPUT_DIR"

# Loop para baixar os arquivos pmed1.txt até pmed40.txt
for i in $(seq 1 40); do
    # Nome do arquivo
    FILENAME="pmed$i.txt"

    # URL completa
    FILE_URL="$BASE_URL/$FILENAME"

    # Caminho para salvar o arquivo localmente
    OUTPUT_FILE="$OUTPUT_DIR/$FILENAME"

    # Download do arquivo usando curl
    echo "Baixando $FILENAME..."
    curl -s -o "$OUTPUT_FILE" "$FILE_URL"

    # Verificar se o download foi bem-sucedido
    if [[ $? -eq 0 ]]; then
        echo "Arquivo $FILENAME salvo em $OUTPUT_FILE"
    else
        echo "Erro ao baixar $FILENAME"
    fi
done

echo "Download concluído!"
