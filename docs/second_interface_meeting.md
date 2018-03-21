## Interfaces

* Core Client
  * `run(RunRequest):Result`
* Gateway
  * `resolve(ResolveRequest):ExecutionGraph`
* GSM
  * `exec(ExecRequest)`
  * `put(DataPutRequest / DataPutPrompt)`

## Request objects table

x indicates that the Request contains the data.

Request | RequestID | Composition(FM) | Composition(Graph) | Policy | DataObjects
-|-|-|-|-|-
Run | x | x | | x | x
Resolve | x | x | | x |
DataPut | x | | | | x
Exec | x | | x |  |