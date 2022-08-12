import os
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declaratibe_base
from sqlalchemy.orm import sessionmaker

POSTGRES_USER=os.getenv("POSTGRES_USER")
POSTGRES_PASSWORD=os.getenv("POSTGRES_PASSWORD")
POSTGRES_SERVER=os.getenv("POSTGRES_SERVER")
POSTGRES_PORT=os.getenv("POSTGRES_PORT")
POSTGRES_DB=os.getenv("POSTGRES_DB")

SQLALCHEMY_DATABASE_URL = "postgresql://{0}:{1}@{2}:{3}/{4}".format(
    POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_SERVER, POSTGRES_PORT, POSTGRES_DB
)

engine = create_engine(
    SQLALCHEMY_DATABASE_URL
)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()