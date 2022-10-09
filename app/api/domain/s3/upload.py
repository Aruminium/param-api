import subprocess
import os
from tkinter import Y

def upload(path: str, s3_target_path) -> None:
  S3_BUCKET = os.environ["S3_BUCKET"]
  subprocess.run(["aws", "s3", "mv", path, f"s3://{S3_BUCKET}/{s3_target_path}"], shell=False)