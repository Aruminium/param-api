from boto3.session import Session

def getFilteredFilenames(file_names=[]):
    if len(file_names) == 0:
        start = ''
    else:
        print(file_names[-1])
        start = file_names[-1]

    response = s3client.list_objects_v2(
        Bucket=BUCKET_NAME,
        Prefix=PREFIX,
        StartAfter=start
    )

    if 'Contents' in response:
        file_names = [content['Key'] for content in response['Contents']]
        if 'IsTruncated' in response:
            return getFilteredFilenames(file_names)
    return file_names

if __name__ == '__main__':

    PROFILE_NAME='profile1'
    BUCKET_NAME = 'test'
    PREFIX = 'test'

    session = Session(profile_name=PROFILE_NAME)
    s3client = session.client('s3')

    print(getFilteredFilenames())