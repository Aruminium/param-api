import openpyxl as px
from .ptj import PtjModel
from typing import List

class Excel:
  def __init__(self,ptj_list: List[PtjModel]):
    self.ptj_list = ptj_list
    self.d_week = {'Sun': '日', 'Mon': '月', 'Tue': '火', 'Wed': '水','Thu': '木', 'Fri': '金', 'Sat': '土'}
    # 単一情報
    self.name = self.ptj_list[0].name
    # 送られてくるデータが昇順であること
    self.start_date = self.ptj_list[0].date.strftime('%Y年%m月%d日')
    self.end_date = self.ptj_list[-1].date.strftime('%Y年%m月%d日')
    self.subject = self.ptj_list[0].working_subject

  def edit(self):
    wb = px.load_workbook("/app/domain/excel/SA.xlsx")
    ws = wb.active
    # 氏名
    ws["X4"].value = "{:　<11}印".format(self.name)
    # 担当科目
    ws["J6"].value = self.subject
    # 活動期間
    ws["J10"].value = f"{self.start_date}　　　～　　　　{self.end_date}"
    for index, ptj in enumerate(self.ptj_list):
      # 日付
      ws[f"B{14+index}"].value = ptj.date.strftime("%m月%d日")
      # 曜日
      ws[f"E{14+index}"].value = self.d_week[ptj.date.strftime("%a")]
      # 開始勤務時間
      # ws[f"H${14+index}"].value = 
      # 終了勤務時間
      # ws[f"L${14+index}"].value = 
      # 活動時間
      # ws[f"O${14+index}"].value = 
      # 活動内容詳細
      ws[f"S{14+index}"].value = f"{ptj.working_content} 休憩時間:{ptj.break_time}"
    wb.save("/app/domain/excel/output.xlsx")