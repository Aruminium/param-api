# from fastapi.testclient import TestClient
# from main import app

# client = testclient.TestClient(app)

# def test_read_json():
#   response = client.post(
#     "/ptj/",
#     headers={"application/json"}
#   )
#   # data = [
#   #   [
#   #     {
#   #       "id": 0,
#   #       "name": "string",
#   #       "date": "2022-09-04",
#   #       "working_start": 0,
#   #       "working_finish": 0,
#   #       "break_time": 0,
#   #       "working_type": "string",
#   #       "working_subject": "string",
#   #       "working_content": "string"
#   #     }
#   # ]

#   # qs = urlencode(data)
#   # resp = client.get(f"/contacts/?{qs}")