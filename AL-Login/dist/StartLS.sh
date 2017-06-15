#!/bin/sh

err=1
until [ $err == 0 ];
do

	java -Xms8m -Xmx32m -ea -Xbootclasspath/p:./libs/jsr166.jar -cp ./libs/*:AL-Login.jar com.aionlightning.loginserver.LoginServer
	err=$?
	lspid=$!
	echo ${lspid} > loginserver.pid
	echo "LoginServer started!"
	sleep 10
done
