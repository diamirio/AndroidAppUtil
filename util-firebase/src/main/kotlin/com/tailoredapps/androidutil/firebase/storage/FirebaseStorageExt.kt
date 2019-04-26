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

package com.tailoredapps.androidutil.firebase.storage

import android.net.Uri
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables
import java.io.File

/**
 * Uploads a file to a [StorageReference] in Firebase Storage. This [Flowable] emits tbe progress percentage of the
 * upload task and completes when the file is uploaded.
 *
 * @param cancellable Boolean Whether or not the upload should be cancelled when the [Flowable] is cancelled.
 */
fun StorageReference.upload(
    fromFile: File,
    cancellable: Boolean = true,
    storageMetaData: (StorageMetadata.Builder.() -> Unit)? = null
): Flowable<Int> = Flowables.create(BackpressureStrategy.LATEST) { emitter ->
    emitter.onNext(0)

    val fileUri = Uri.fromFile(fromFile)
    val task = when (storageMetaData) {
        null -> putFile(fileUri)
        else -> putFile(fileUri, StorageMetadata.Builder().apply { storageMetaData(this) }.build())
    }

    task.addOnFailureListener(emitter::onError)
    task.addOnSuccessListener { emitter.onComplete() }
    task.addOnProgressListener {
        try {
            emitter.onNext((100 * it.bytesTransferred / it.totalByteCount).toInt())
        } catch (e: ArithmeticException) {
            emitter.onNext(0)
        }
    }

    if (cancellable) {
        emitter.setCancellable { task.cancel() }
    }
}

/**
 * Downloads a file from the [StorageReference]. This [Flowable] emits the progress percentage of the download
 * task and completes when the file is downloaded.
 *
 * @param cancellable Boolean Whether or not the download should be cancelled when the [Flowable] is cancelled.
 */
fun StorageReference.download(
    intoFile: File,
    cancellable: Boolean = true
): Flowable<Int> = Flowables.create(BackpressureStrategy.LATEST) { emitter ->
    emitter.onNext(0)

    val task = getFile(intoFile)
    task.addOnFailureListener(emitter::onError)
    task.addOnSuccessListener { emitter.onComplete() }
    task.addOnProgressListener {
        try {
            emitter.onNext((100 * it.bytesTransferred / it.totalByteCount).toInt())
        } catch (e: ArithmeticException) {
            emitter.onNext(0)
        }
    }

    if (cancellable) {
        emitter.setCancellable { task.cancel() }
    }
}