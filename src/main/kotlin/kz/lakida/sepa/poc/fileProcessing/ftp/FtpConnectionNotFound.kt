package kz.lakida.sepa.poc.fileProcessing.ftp

class FtpConnectionNotFound : Exception("FTP connection was not found for this thread, did you forget to wrap your " +
        "method with @WithFtp?")
