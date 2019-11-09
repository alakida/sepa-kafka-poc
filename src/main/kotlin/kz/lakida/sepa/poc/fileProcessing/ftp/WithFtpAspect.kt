package kz.lakida.sepa.poc.fileProcessing.ftp

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class WithFtpAspect(
    private val ftpConnectionHolder: FtpConnectionHolder,
    private val ftpConnectionFactory: FtpConnectionFactory
) {
    @Around("@annotation(kz.lakida.sepa.poc.fileProcessing.ftp.WithFtp)")
    private fun runWithFtp(function: ProceedingJoinPoint): Any? {
        val connection = ftpConnectionFactory.createNewConnection()
        ftpConnectionHolder.put(connection)
        connection.use {
            val returnValue = function.proceed()
            ftpConnectionHolder.remove()
            return returnValue
        }
    }
}
