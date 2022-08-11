FROM python:3.9-alpine
ENV PYTHONUNBUFFERED=1

RUN /usr/local/bin/python -m pip install --upgrade pip

WORKDIR /docker/app/
COPY docker/app/requirements.txt /docker/app
RUN pip install -r requirements.txt

CMD ["uvicorn", "main:app", "--reload", "--host", "0.0.0.0", "--port", "5000"]