#!/bin/bash
set -e

# Metadata
BASE_PATH=src/main/java/javacup
GROUP_ID=com.github.jhoenicke
VERSION=1.2
PACKAGING=jar

declare -A ARTIFACTS=(
    ["javacup"]="${BASE_PATH}/jh-javacup-1.2.jar"
    ["javacup-runtime"]="${BASE_PATH}/jh-javacup-1.2-runtime.jar"
)

M2_REPO="${HOME}/.m2/repository"
GROUP_PATH=$(echo "$GROUP_ID" | tr '.' '/')

# Track whether any artifact was installed
installed=false

for ARTIFACT_ID in "${!ARTIFACTS[@]}"; do
    JAR_PATH="${ARTIFACTS[$ARTIFACT_ID]}"
    TARGET_PATH="${M2_REPO}/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.${PACKAGING}"

    if [ -f "$TARGET_PATH" ]; then
        echo "[INFO] $ARTIFACT_ID already installed in local Maven repository."
    else
        echo "[INFO] Installing $JAR_PATH as $ARTIFACT_ID into local Maven repository..."
        if [ ! -f "$JAR_PATH" ]; then
            echo "[ERROR] Missing JAR at $JAR_PATH"
            exit 1
        fi

        mvn install:install-file \
            -Dfile="$JAR_PATH" \
            -DgroupId="$GROUP_ID" \
            -DartifactId="$ARTIFACT_ID" \
            -Dversion="$VERSION" \
            -Dpackaging="$PACKAGING"

        installed=true
    fi
done

# Step 2: Generate sources if installation occurred
if [ "$installed" = true ]; then
    echo "[INFO] Running mvn clean generate-sources -Pgenerate..."
    mvn clean generate-sources -Pgenerate
else
    echo "[INFO] No new artifacts installed. Skipping generation step."
fi

# Step 3: Always build the project
echo "[INFO] Running mvn clean install..."
mvn clean package
