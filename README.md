# Symantec-MD5-Hash-Auto-Submission

This program created after my resignation at HKBEA aims to help colleagues in Deskside Support Team. 
Its purpose is to fill in the form on the Synmantec website for the suspected infected file examination request and to retrieve the corresponding tracking number automatically. Be noted that this only works when the website does not contain captcha validation.

This hardcoded program only works on the website: Symantec - Upload a suspected infected file (BCS).
URL: https://submit.symantec.com/websubmit/bcs.cgi

The bot with absolute correctness was written quickly in roughly 2 days. Therefore, be careful on the input (MD5) in the text area located on the bottom left corner - each MD5 takes a row, with no extra character such as " ". As this is my first time writing a GUI, the design was not too decent.

12 Feb update: Import Hash function created. MD5 format will be checked. Trailing space will be omitted.

There are 2 ways to run this program. 
1) Download the bot along with bat/exe file of the corresponding version. 
   Click on bat/exe file to open.
   
2) Open cmd.exe / terminal.
   Go to the directory which holds MD5_Bot_Java(Version).jar
   execute jar file.
   
   Commands for (2):
   "cd filepath"
   "java -jar filename"
