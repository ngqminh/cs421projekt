#!/usr/bin/python
import sys
import ibm_db

#returns the amount of money taxed per month 
def fetch_all(stmt):
	t_list = []
	t_row = ibm_db.fetch_tuple(stmt)
	while t_row:
		t_list.append(t_row)
		t_row = ibm_db.fetch_tuple(stmt)
	return t_list

def taxable_cash_dolla(conn):
	sql = "SELECT processing_fee, transaction_date FROM transaction ORDER BY transaction_date ASC"
	stmt = ibm_db.exec_immediate(conn, sql)
	t_list = fetch_all(stmt)
	t_dict = {}
	for i in t_list:
		date = i[1][:7]
		if date in t_dict:
			t_dict[date] = t_dict[date] + float(i[0])
		else:
			t_dict[date] = float(i[0])

	for i in t_dict:
		t_dict[i] = str(round(t_dict[i] * 0.15, 2))
	return t_dict


def main():
	conn = ibm_db.connect("cs421","","")
	taxes = taxable_cash_dolla(conn)

	for i in taxes:
		print "Month: " + i + ", Taxes paid: " + taxes[i]


	ibm_db.close(conn)

if __name__ == "__main__":
    main()
