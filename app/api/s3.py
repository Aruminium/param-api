import boto3

def uploadPdfToS3(upload_file_name: str, storage_path: str) -> None:
  AWS_REGION ="ap-northeast-1"
  client = boto3.client('s3',region_name=AWS_REGION)
  bucket_name = "param-api-pdfs"
  upload_file = f"app/api/domain/excel/files/{upload_file_name}"
  client.upload_file(upload_file,bucket_name,storage_path)
  print("Upload to s3 bucket completed!")