import MySQLdb


def mysqlmain():
	db = MySQLdb.connect(host="mysql3.gear.host",  # your host, usually localhost
	                     user="jackfury",  # your username
	                     passwd="!rrrrrr",  # your password
	                     db="malice")  # name of the data base
	
	# you must create a Cursor object. It will let
	#  you execute all the queries you need
	cur = db.cursor()
	
	# Use all the SQL you like
	cur.execute("insert into exponents values (13,133)")
	cur.execute("SELECT * from exponents")
	
	# print all the first cell of all the rows
	for row in cur.fetchall():
		print(row)
	db.commit()
	db.close()


mysqlmain()
