#!/usr/bin/env bash
set -euf -o pipefail

# Import signing key
echo "${TEAM_JAVA_SIGN_PASSWORD}" > pass.txt
echo "${TEAM_JAVA_SIGN_KEY}" > sign.key
gpg --batch --passphrase-file pass.txt --import sign.key

# Release
mvn -P release -Dgpg.passphrase="${TEAM_JAVA_SIGN_PASSWORD}" -s ci/settings.xml -f instana-java-sdk/pom.xml clean verify deploy

# Move version file to output
cp instana-java-sdk/target/classes/version.txt ../version/
