# readable

Hypermedia API to track the books you read, share feedbacks, get recommendations on books to read.

### ROOT URL
Discover the API in browser following next URL:
http://readable-api.herokuapp.com

### Supported Media types:  
- **application/vnd.siren+json** (https://github.com/kevinswiber/siren)
- **application/xhtml+xml** (http://www.w3.org/TR/2001/REC-xhtml11-20010531/)

### Supported entity classes:
- item
- list
- https://schema.org/Book
- https://schema.org/Person

### Supported link/operation relations:
- navigation - group of items that should appear in the menu
- first
- last
- previous
- next
- add
- delete
- search

### Authentication mechanism:
HTTP Basic Authentication

### Resource has:
- class
- properties
- embedded resources (links to embedded resources)
- links (holds class of target resource)
- actions (modifies state of current resource)

### Client development instructions:
- Page is showing one resource at a time.
- Page recognizes resource by class: shows properties, embedded resources, links and operations nicely.
- Page replaces the resource currently shown upon redirect by clicking on a link.
- Page update currently shown resource upon action invocation.

### FAQ
* Should we show unknown resources? - No
* Should we show unknown links? - Yes 
* Should we show unknown operations? - Yes

