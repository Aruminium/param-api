from fastapi import APIRouter, Depends, HTTPException
from fastapi.responses import FileResponse
from sqlalchemy.ext.asyncio import AsyncSession
import cruds.subject as subject_crud
from db import get_db
import schemas.subject as subject_schema
from typing import List

router = APIRouter(
  prefix="/subjects",
  tags=["subjects"]
)

@router.get("/", response_model=List[subject_schema.Subject])
async def list_subject(db: AsyncSession = Depends(get_db)):
  return await subject_crud.get_subjects(db)

@router.post("/", response_model=subject_schema.CreateSubjectResponse)
async def create_subject(subject_body: subject_schema.CreateSubject, db: AsyncSession = Depends(get_db)):
  return await subject_crud.create_subject(db, subject_body)

@router.put("/{subject_id}", response_model=subject_schema.CreateSubjectResponse)
async def update_subject(subject_id: int, subject_body: subject_schema.CreateSubject, db: AsyncSession = Depends(get_db)):
  subject = await subject_crud.get_subject_detail(db, subject_id=subject_id)
  if subject is None:
    raise HTTPException(status_code=404, detail="Subject not found")
  return await subject_crud.update_subject(db, subject_body, original=subject)

@router.delete("/{subject_id}", response_model=None)
async def delete_subject(subject_id: int, db: AsyncSession = Depends(get_db)):
  subject = await subject_crud.get_subject_detail(db, subject_id=subject_id)
  if subject is None:
    raise HTTPException(status_code=404, detail="Subject not found")
  return await subject_crud.delete_subject(db, original=subject)

@router.post("/test")
async def test():
  response = FileResponse(path="/app/api/domain/excel/山田太郎-2022年9月.pdf", filename="test.pdf")
  return response