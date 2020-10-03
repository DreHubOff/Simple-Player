package com.example.simpleplayer.ui.film

import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Error
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2core.DownloadBlock

 abstract class MyFetchListener: FetchListener {
     override fun onAdded(download: Download) {

     }

     override fun onCancelled(download: Download) {

     }

     override fun onCompleted(download: Download) {

     }

     override fun onDeleted(download: Download) {

     }

     override fun onDownloadBlockUpdated(
         download: Download,
         downloadBlock: DownloadBlock,
         totalBlocks: Int
     ) {

     }

     override fun onError(download: Download, error: Error, throwable: Throwable?) {

     }

     override fun onPaused(download: Download) {

     }

     override fun onProgress(
         download: Download,
         etaInMilliSeconds: Long,
         downloadedBytesPerSecond: Long
     ) {

     }

     override fun onQueued(download: Download, waitingOnNetwork: Boolean) {

     }

     override fun onRemoved(download: Download) {

     }

     override fun onResumed(download: Download) {

     }

     override fun onStarted(
         download: Download,
         downloadBlocks: List<DownloadBlock>,
         totalBlocks: Int
     ) {

     }

     override fun onWaitingNetwork(download: Download) {

     }

 }