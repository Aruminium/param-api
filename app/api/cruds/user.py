from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select
from sqlalchemy.engine import Result
from typing import Tuple
import models.user as user_model

async def get_user(db: AsyncSession, request_student_number: str) -> Tuple[str, str]:
  result: Result = await db.execute(
    select(
      user_model.User.student_number,
      user_model.User.password
    ).filter(user_model.User.student_number == request_student_number)
  )
  return result.first()
