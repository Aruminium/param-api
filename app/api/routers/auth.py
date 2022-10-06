import os
from datetime import datetime, timedelta
from fastapi import APIRouter, HTTPException, status
from fastapi.param_functions import Depends
from fastapi.security.oauth2 import OAuth2PasswordRequestForm
from sqlalchemy.ext.asyncio import AsyncSession
from db import get_db
from auth import oauth2
import cruds.user as user_cruds
import hashlib
from enum import Enum
from schemas.token import Token

class GetUserTuple(Enum):
  STUDENT_NUMBER = 0
  PASSWORD = 1

router = APIRouter(
  tags=['authentication']
)

ADMIN_STUDENT_NUMBER = os.environ['ADMIN_STUDENT_NUMBER']
ADMIN_PASSWORD = os.environ['ADMIN_PASSWORD']
ACCESS_TOKEN_EXPIRE_MINUTES = os.environ['ACCESS_TOKEN_EXPIRE_MINUTES']

@router.post("/token", response_model=Token)
async def get_token(request: OAuth2PasswordRequestForm = Depends(), db: AsyncSession = Depends(get_db)):
  if not request.username == ADMIN_STUDENT_NUMBER:
    raise HTTPException(
      status_code=status.HTTP_404_NOT_FOUND,
      detail="Invalid credentials"
    )
  if not request.password == ADMIN_PASSWORD:
    raise HTTPException(
      status_code=status.HTTP_404_NOT_FOUND,
      detail='Incorrect password'
    )

  user = await user_cruds.get_user(db, request.username)

  if not user:
    raise HTTPException(
      status_code=status.HTTP_404_NOT_FOUND,
      detail="Invalid credentials"
    )
  pass_hash = hashlib.sha256(request.password.encode()).hexdigest()
  if not user[GetUserTuple.PASSWORD.value] == pass_hash:
    raise HTTPException(
      status_code=status.HTTP_404_NOT_FOUND,
      detail='Incorrect password'
    )
  access_token_expires = timedelta(minutes=float(ACCESS_TOKEN_EXPIRE_MINUTES))
  access_token = oauth2.create_access_token(data={'sub': user[GetUserTuple.STUDENT_NUMBER.value]}, expires_delta=access_token_expires)
  return {
    'access_token': access_token,
    'token_type': 'bearer'
  }