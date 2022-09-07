from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
import cruds.subject as subject_crud
from db import get_db
import schemas.subject as subject_schema
from typing import List

router = APIRouter()

@router.get("/subjects", response_model=List[subject_schema.Subject])
async def list_subject(db: AsyncSession = Depends(get_db)):
  return await subject_crud.get_subjects(db)

@router.post("/subjects", response_model=subject_schema.CreateSubjectResponse)
async def create_subject(subject_body: subject_schema.CreateSubject, db: AsyncSession = Depends(get_db)):
  return await subject_crud.create_subject(db, subject_body)

@router.put("/subjects/{subject_id}", response_model=subject_schema.CreateSubjectResponse)
async def update_subject(subject_id: int, subject_body: subject_schema.CreateSubject, db: AsyncSession = Depends(get_db)):
  subject = await subject_crud.get_subject(db, subject_id=subject_id)
  if subject is None:
    raise HTTPException(status_code=404, detail="Subject not found")
  return await subject_crud.update_subject(db, subject_body, original=subject)

@router.delete("/subjects/{subject_id}", response_model=None)
async def delete_subject(subject_id: int, db: AsyncSession = Depends(get_db)):
  subject = await subject_crud.get_subject(db, subject_id=subject_id)
  if subject is None:
    raise HTTPException(status_code=404, detail="Subject not found")
  return await subject_crud.delete_subject(db, original=subject)
