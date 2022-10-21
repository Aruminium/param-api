from fastapi.security import OAuth2PasswordBearer
from typing import Optional
from datetime import datetime, timedelta
from sqlalchemy.ext.asyncio import AsyncSession
from fastapi.param_functions import Depends
from datetime import datetime, timedelta
from jose import jwt
from jose.exceptions import JWTError
from db import get_db
import cruds.user as user_cruds
from fastapi import HTTPException, status
import os
from schemas.user import User

SECRET_KEY = os.environ['SECRET_KEY']
ALGORITHM = os.environ['ALGORITHM']

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")


def create_access_token(data: dict, expires_delta: Optional[timedelta] = None):
  to_encode = data.copy()
  if expires_delta:
    expire = datetime.utcnow() + expires_delta
  else:
    expire = datetime.utcnow() + timedelta(minutes=15)
  to_encode.update({"exp": expire})
  encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
  return encoded_jwt

async def get_current_user(token: str = Depends(oauth2_scheme), db: AsyncSession = Depends(get_db)):
  credentials_exception = HTTPException(
    status_code=status.HTTP_401_UNAUTHORIZED,
    detail='Colud not validate credentials',
    headers={'WWW-Authenticate': "Bearer"}
  )
  try:
    payload = jwt.decode(token, SECRET_KEY, algorithm=ALGORITHM)
    student_number: str = payload.get("sub")
    if student_number is None:
      raise credentials_exception
  except JWTError:
      raise credentials_exception
  user = await user_cruds.get_user(db, student_number)
  if user is None:
      raise credentials_exception
  return user

async def get_current_active_user(current_user: User = Depends(get_current_user)):
  if current_user.disabled:
    raise HTTPException(status_code=400, detail="Inactive user")
  return current_user

