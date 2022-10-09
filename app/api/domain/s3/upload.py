import subprocess
import os
from tkinter import Y

def upload(path: str, year:str, month: str, subject: str) -> None:
  S3_BUCKET = os.environ["S3_BUCKET"]
  subprocess.run(["aws", "s3", "mv", path, f"s3://{S3_BUCKET}/{year}/{month}/{subject}/"], shell=False)