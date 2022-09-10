from sqlalchemy.ext.asyncio import AsyncSession
import models.subject as subject_model
import schemas.subject as subject_schema
from typing import List, Tuple, Optional
from sqlalchemy import select
from sqlalchemy.engine import Result

async def create_subject(db: AsyncSession, create_subject: subject_schema.CreateSubject) -> subject_model.Subject:
  subject = subject_model.Subject(**create_subject.dict())
  db.add(subject)
  await db.commit()
  await db.refresh(subject)
  return subject

async def get_subjects(db: AsyncSession) -> List[Tuple[int, str, str]]:
  result: Result = await (
    db.execute(
      select(
        subject_model.Subject.id,
        subject_model.Subject.name,
        subject_model.Subject.teacher_name,
      ).order_by(subject_model.Subject.id)
    )
  )
  return result.all()

async def get_subject_name(db: AsyncSession, name: str) -> Tuple[int, str, str]:
  result: Result = await db.execute(
    select(
      subject_model.Subject.id,
      subject_model.Subject.name,
      subject_model.Subject.teacher_name,
    ).filter(subject_model.Subject.name == name)
  )
  return result.all()

async def get_subject_detail(db: AsyncSession, subject_id: int) -> Optional[subject_model.Subject]:
  result: Result = await db.execute(
    select(subject_model.Subject).filter(subject_model.Subject.id == subject_id)
  )
  subject: Optional[Tuple[subject_model.Subject]] = result.first()
  return subject[0] if subject is not None else None

async def update_subject(db: AsyncSession, create_subject: subject_schema.CreateSubject, original: subject_model.Subject):
  original.name = create_subject.name
  original.teacher_name = create_subject.teacher_name
  await db.commit()
  await db.refresh(original)
  return original

async def delete_subject(db: AsyncSession, original: subject_model.Subject) -> None:
  await db.delete(original)
  await db.commit()
