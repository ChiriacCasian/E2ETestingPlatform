FROM --platform=linux/amd64 jetbrains/teamcity-agent:2025.03.3-linux-sudo AS base

USER root
WORKDIR /app
ARG DEBIAN_FRONTEND=noninteractive
ENV DEBIAN_FRONTEND=noninteractive \
TZ=Etc/UTC

RUN apt-get update && \
            apt-get install -y --no-install-recommends \
            python3 \
            python3-pip \
            python3-venv \
            xvfb \
            x11-utils \
            x11vnc \
            fluxbox \
            android-tools-adb \
            nodejs npm \
            android-sdk android-sdk-platform-tools \
            curl \
            novnc \
            websockify \
            wget \
            unzip \
            jq