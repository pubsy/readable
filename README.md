# readable

Hypermedia API to track the books you read, share feedbacks, get recommendations on books to read.

### ROOT URL
Discover the API in browser following next URL:
http://readable-api.herokuapp.com

### Supported Media types:  
- **application/vnd.siren+json** (https://github.com/kevinswiber/siren)
- **application/xhtml+xml** (http://www.w3.org/TR/2001/REC-xhtml11-20010531/)

### Supported resource classes:
- BookResource (https://schema.org/Book)
- BooksListResource (collection of BookResources)
- UserResource (https://schema.org/Person)
- UsersListResource  (collection of UserResources)

### Supported link/action relations:
- navigation - group of links and actions that should appear in navigation menu
- pagination - group of links to paginate the collection resources
- search - action used to search/filter resource collections

### Authentication mechanism:
HTTP Basic Authentication

### Client development instructions:
- Page is showing one resource at a time.
- Page recognizes resource by class: shows properties, embedded resources, links and operations nicely.
- Page replaces the resource currently shown upon redirect by clicking on a link.
- Page update currently shown resource upon action invocation.

