IRRecord.exe -d IRToy.dll -f tmp.cfg
copy /b C:\WinLirc\plugins\lircd.conf /plugins/tmp.cfg C:\WinLirc\plugins\lircd.conf
del /plugins/tmp.cfg  