b: docker-compose.yml app/Dockerfile
	docker compose build

bn:
	docker compose build --no-cache

up:
	docker compose up -d

d:
	docker compose down

r:
	docker compose down
	docker compose up -d