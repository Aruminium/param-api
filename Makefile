build: docker-compose.yml app/Dockerfile
	docker compose build

up:
	docker compose up -d

down:
	docker compose down