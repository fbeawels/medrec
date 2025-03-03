userName = "weblogic"
passWord = "weblogic"
url="t3://localhost:7101"

connect(userName, passWord, url)

progress= deploy(
	'medrecEar', 
	'c:\medrec_tutorial\dist\medrecEar', 
	targets='MedRecServer', 
	subModuleTargets='MedRecJMSServer@MedRecAppScopedJMS@MedRecJMSServer',
	securityModel='CustomRolesAndPolicies' )


progress.printStatus()

disconnect()

exit()
