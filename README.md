## Spring Batch example with kotlin
Batch Application Example with Spring Batch, Chunked Tasklet

---

### Environment & Lib
- Spring Boot 2.4.1
- Spring Batch
- Spring Data Jpa
- RDB(MariaDB)

### Description
- Boilerplate for Spring Batch
- Example Code
    - Simple Tasklet
    - JSON File Read  
    - Chunked Oriented Tasklet
      - Custom Item Reader
      - JPA Paging Item Reader
    - Spring Schedule
  
### Use Case
1. Use with Spring Schedule
```yaml
# application.yml
schedule:
  active: true
```
2. Use with Command
```yaml
# application.yml
spring:
  batch:
    job:
      names: ${job.name:NONE}

schedule:
  active: false
```
```bash
# set job name and parameter.
java -jar --job.name=JPA_PAGING_IR_JOB createDate=2020-10-14 ./batch.jar
```