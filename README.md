# SEPA processing with Kafka PoC

### How to run locally
- Run `docker-compose up` to start up Kafka and FTP server
- Add `127.0.0.1 kafka` to your */etc/hosts* file
- Run `./gradlew bootRun` to start up the app
- Put json file of the following format into `ftp_folder` directory:
```json
{
  "transactions": [
    {
      "originatorId": "originator",
      "recipientId": "recipient",
      "amount": 100.0,
      "settlementDate": "2019-11-09 19:40:42+0000"
    }
  ]
}
```
- Verify data coming to `new_transactions` Kafka topic

    To do that you can use `kafkacat -b kafka -t new_transactions -f '%k:%s\r\n'`
