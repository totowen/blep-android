#!/bin/sh

# ת��ϵͳǩ������
./keytool-importkeypair -k blep-android.jks -p s12152839, -pk8 platform.pk8 -cert platform.x509.pem -alias blep-android

# demo.jks : ǩ���ļ�
# 123456 : ǩ���ļ�����
# platform.pk8��platform.x509.pem : ϵͳǩ���ļ�
# demo : ǩ���ļ�����