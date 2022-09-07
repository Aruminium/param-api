FROM python:3.9-alpine
ENV PYTHONUNBUFFERED=1

RUN /usr/local/bin/python -m pip install --upgrade pip
RUN apk update \
    && apk add --no-cache --virtual .build-rundeps \
        linux-headers \
        build-base \
        mariadb-connector-c-dev \
        libxml2-dev \
        libxslt-dev \
    && apk add --upgrade --no-cache --virtual .libreoffice-rundeps \
        libreoffice \
        libreoffice-base \
        libreoffice-lang-ja \
        font-noto-cjk

RUN apk add libpq-dev

RUN pip install poetry

WORKDIR /app
COPY pyproject.toml* poetry.lock* ./
RUN poetry install
RUN poetry config virtualenvs.in-project false
RUN if [ -f pyproject.toml ]; then poetry install; fi

WORKDIR /app/api
CMD ["poetry", "run", "uvicorn", "main:app", "--reload", "--host", "0.0.0.0", "--port", "5000"]