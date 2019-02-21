/*
 * Copyright 2019 Florian Schuster.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tailoredapps.androidutil.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi


/**
 * Android Manifest Permissions. Check each one for AndroidManifest.xml tag.
 */
sealed class Permission(val manifestPermission: String) {
    interface Group {
        val groupName: String
        val all: List<Permission>
    }

    /**
     * Tag: <uses-permission android:name="android.permission.CAMERA" />
     */
    object Camera : Permission(Manifest.permission.CAMERA)

    /**
     * Tag: <pre> <uses-permission android:name="android.permission.RECORD_AUDIO" /> </pre>
     */
    object Microphone : Permission(Manifest.permission.RECORD_AUDIO)

    /**
     * Tag: <pre> <uses-permission android:name="android.permission.BODY_SENSORS" /> </pre>
     */
    object Sensors : Permission(Manifest.permission.BODY_SENSORS)

    sealed class Calendar(manifestPermission: String) : Permission(manifestPermission) {
        /**
         * Tag: <pre> <uses-permission android:name="android.permission.READ_CALENDAR" /> </pre>
         */
        object Read : Calendar(Manifest.permission.READ_CALENDAR)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.WRITE_CALENDAR" /> </pre>
         */
        object Write : Calendar(Manifest.permission.WRITE_CALENDAR)

        companion object : Group {
            override val groupName: String = Manifest.permission_group.CALENDAR
            override val all: List<Permission> = listOf(Read, Write)
        }
    }

    sealed class CallLog(manifestPermission: String) : Permission(manifestPermission) {
        /**
         * Tag: <pre> <uses-permission android:name="android.permission.READ_CALL_LOG" /> </pre>
         */
        object Read : CallLog(Manifest.permission.READ_CALL_LOG)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.WRITE_CALL_LOG" /> </pre>
         */
        object Write : CallLog(Manifest.permission.WRITE_CALL_LOG)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" /> </pre>
         */
        object ProcessOutgoingCalls : CallLog(Manifest.permission.PROCESS_OUTGOING_CALLS)

        companion object : Group {
            @RequiresApi(Build.VERSION_CODES.P)
            override val groupName: String = Manifest.permission_group.CALL_LOG
            override val all: List<Permission> = listOf(Read, Write, ProcessOutgoingCalls)
        }
    }

    sealed class Contacts(manifestPermission: String) : Permission(manifestPermission) {
        /**
         * Tag: <pre> <uses-permission android:name="android.permission.READ_CONTACTS" /> </pre>
         */
        object Read : Contacts(Manifest.permission.READ_CONTACTS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.WRITE_CONTACTS" /> </pre>
         */
        object Write : Contacts(Manifest.permission.WRITE_CONTACTS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.GET_ACCOUNTS" /> </pre>
         */
        object Accounts : Contacts(Manifest.permission.GET_ACCOUNTS)

        companion object : Group {
            @RequiresApi(Build.VERSION_CODES.M)
            override val groupName: String = Manifest.permission_group.CONTACTS
            override val all: List<Permission> = listOf(Read, Write, Accounts)
        }
    }

    sealed class Location(manifestPermission: String) : Permission(manifestPermission) {
        /**
         * Tag: <pre> <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> </pre>
         */
        object Fine : Location(Manifest.permission.ACCESS_FINE_LOCATION)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> </pre>
         */
        object Coarse : Location(Manifest.permission.ACCESS_COARSE_LOCATION)

        companion object : Group {
            override val groupName: String = Manifest.permission_group.LOCATION
            override val all: List<Permission> = listOf(Fine, Coarse)
        }
    }

    sealed class Phone(manifestPermission: String) : Permission(manifestPermission) {

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.READ_PHONE_STATE" /> </pre>
         */
        object ReadState : Phone(Manifest.permission.READ_PHONE_STATE)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.READ_PHONE_NUMBERS" /> </pre>
         */
        @RequiresApi(Build.VERSION_CODES.O)
        object ReadNumbers : Phone(Manifest.permission.READ_PHONE_NUMBERS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.ANSWER_PHONE_CALLS" /> </pre>
         */
        @RequiresApi(Build.VERSION_CODES.O)
        object Answers : Phone(Manifest.permission.ANSWER_PHONE_CALLS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.CALL_PHONE" /> </pre>
         */

        object Call : Phone(Manifest.permission.CALL_PHONE)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.ADD_VOICEMAIL" /> </pre>
         */
        object VoiceMail : Phone(Manifest.permission.ADD_VOICEMAIL)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.USE_SIP" /> </pre>
         */
        object UseSip : Phone(Manifest.permission.USE_SIP)

        companion object : Group {
            @RequiresApi(Build.VERSION_CODES.M)
            override val groupName: String = Manifest.permission_group.PHONE
            override val all: List<Permission> = listOf(ReadState, ReadNumbers, Answers, Call, VoiceMail, UseSip)
        }
    }

    sealed class SMS(manifestPermission: String) : Permission(manifestPermission) {
        /**
         * Tag: <pre> <uses-permission android:name="android.permission.SEND_SMS" /> </pre>
         */
        object Send : SMS(Manifest.permission.SEND_SMS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.READ_SMS" /> </pre>
         */
        object Read : SMS(Manifest.permission.READ_SMS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.RECEIVE_SMS" /> </pre>
         */
        object Receive : SMS(Manifest.permission.RECEIVE_SMS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.RECEIVE_MMS" /> </pre>
         */
        object ReceiveMMS : SMS(Manifest.permission.RECEIVE_MMS)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH" /> </pre>
         */
        object ReceiveWAPPush : SMS(Manifest.permission.RECEIVE_WAP_PUSH)

        companion object : Group {
            @RequiresApi(Build.VERSION_CODES.M)
            override val groupName: String = Manifest.permission_group.SMS
            override val all: List<Permission> = listOf(Send, Read, Receive, ReceiveMMS, ReceiveWAPPush)
        }
    }

    sealed class Storage(manifestPermission: String) : Permission(manifestPermission) {
        /**
         * Tag: <pre> <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> </pre>
         */
        object Read : Storage(Manifest.permission.READ_EXTERNAL_STORAGE)

        /**
         * Tag: <pre> <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> </pre>
         */
        object Write : Storage(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        companion object : Group {
            override val groupName: String = Manifest.permission_group.STORAGE
            override val all: List<Permission> = listOf(Read, Write)
        }
    }
}