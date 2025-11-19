#!/bin/bash
set -e

# Metadata
GROUP_ID=com.github.jhoenicke
VERSION=1.2
PACKAGING=jar

declare -A ARTIFACTS=(
    ["javacup"]="jh-javacup-1.2.jar"
    ["javacup-runtime"]="jh-javacup-1.2-runtime.jar"
)

M2_REPO="${HOME}/.m2/repository"
GROUP_PATH=$(echo "$GROUP_ID" | tr '.' '/')

# Cleanup
for ARTIFACT_ID in "${!ARTIFACTS[@]}"; do
    ARTIFACT_DIR="${M2_REPO}/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}"
    if [ -d "$ARTIFACT_DIR" ]; then
        echo "[INFO] Deleting $ARTIFACT_DIR..."
        rm -rf "$ARTIFACT_DIR"
    else
        echo "[INFO] No installed artifact found for $ARTIFACT_ID."
    fi
done

# Optionally remove parent folders if empty
for ARTIFACT_ID in "${!ARTIFACTS[@]}"; do
    ARTIFACT_PARENT="${M2_REPO}/${GROUP_PATH}/${ARTIFACT_ID}"
    if [ -d "$ARTIFACT_PARENT" ] && [ -z "$(ls -A "$ARTIFACT_PARENT")" ]; then
        echo "[INFO] Removing empty directory $ARTIFACT_PARENT..."
        rmdir "$ARTIFACT_PARENT"
    fi
done

# Check if GROUP_PATH itself is now empty
GROUP_DIR="${M2_REPO}/${GROUP_PATH}"
while [ "$GROUP_DIR" != "$M2_REPO" ] && [ -d "$GROUP_DIR" ] && [ -z "$(ls -A "$GROUP_DIR")" ]; do
    echo "[INFO] Removing empty directory $GROUP_DIR..."
    rmdir "$GROUP_DIR"
    GROUP_DIR=$(dirname "$GROUP_DIR")
done

echo "[INFO] Cleanup complete."
