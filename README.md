# Global Class Offering Booking System

## Overview

A Spring Boot based backend service for a global live-learning platform where teachers conduct online classes for students across different countries and timezones.

The platform allows teachers to create course offerings with multiple sessions, while parents can browse, book, and manage their enrollments.

### Key Goals

* Clean backend architecture
* Timezone-aware scheduling
* Booking conflict detection
* Concurrent booking protection
* Data consistency
* Robust exception handling

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven
* Lombok

---

## Features

### Admin

* Register Teacher
* Register Parent

### Teacher

* Create Offering
* Add Sessions to Offering
* View Teacher Offerings

### Parent

* View Available Offerings
* Book Offering
* View My Bookings

---

## Domain Model

### Teacher

Represents an instructor conducting classes.

### Parent

Represents a parent/student account.

### Course

Represents a learning program.

Examples:

* Python Coding
* Art Drawing
* Public Speaking

### Offering

Represents a schedulable version of a course.

Examples:

* Saturday Batch
* Evening Batch
* Summer Camp

### Session

Represents actual meeting schedules belonging to an offering.

Example:

* June 6 → 6 PM – 7 PM
* June 13 → 6 PM – 7 PM
* June 20 → 6 PM – 7 PM

### Booking

Represents a parent booking an offering.

> Booking occurs at the Offering level, not the Session level.

---

## Database Constraints

### Booking

A parent cannot book the same offering twice.

```sql
UNIQUE(parent_id, offering_id)
```

### Offering

A teacher cannot create duplicate offerings for the same course.

```sql
UNIQUE(teacher_id, course_id)
```

---

## API Endpoints

### Admin APIs

#### Register User

```http
POST /api/admin/register-user
```

Supported user types:

* TEACHER
* PARENT

---

### Teacher APIs

#### Create Offering

```http
POST /api/teacher/create-offering
```

#### Add Sessions

```http
POST /api/teacher/add-sessions
```

#### View Teacher Offerings

```http
POST /api/teacher/view-offerings
```

---

### Parent APIs

#### Get Available Offerings

```http
GET /api/parent/get-available-offerings
```

Returns offerings having at least one future session.

#### Book Offering

```http
POST /api/parent/book-offering
```

#### View My Bookings

```http
POST /api/parent/view-my-bookings
```

---

## Booking Conflict Detection

<img width="1536" height="1024" alt="BookingConflictDetection" src="https://github.com/user-attachments/assets/427997a8-9cbc-4dce-a4ff-6840756c9746" />


The system validates every session of a new offering against sessions from already booked offerings.

This prevents parents from booking overlapping class schedules.

---

## Timezone Handling

<img width="1536" height="1024" alt="TimeZone Handling" src="https://github.com/user-attachments/assets/8ef4bf88-1f7c-4222-9ce7-651aa24347b9" />


### UTC Time Storage

All session timings are stored using:

```java
Instant
```

Benefits:

* Eliminates timezone ambiguity
* Supports global scheduling
* Ensures consistent storage

### Display Conversion

When a parent books an offering:

```java
ZoneId.of(request.getTimezone())
```

is used to convert session timings into the parent's local timezone.

Benefits:

* Teacher timezone independence
* Global compatibility
* Consistent storage format

---

## Concurrency Handling

<img width="1536" height="1024" alt="Concurrency Handling" src="https://github.com/user-attachments/assets/338e1ea4-01f2-4935-b3f9-36b30632512c" />


### Transactional Booking Workflow

```java
@Transactional
```

ensures booking consistency.

### Pessimistic Locking

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
```

is applied before overlap validation.

Example:

```sql
select p
from Parent p
where p.id = :parentId
```

### Why Pessimistic Locking?

<img width="1536" height="1024" alt="Why-Pessimistic-Locking" src="https://github.com/user-attachments/assets/406a1fe6-cda8-4233-b3c3-64801df29331" />


Without locking:

1. Request A checks conflicts
2. Request B checks conflicts
3. Both find no conflicts
4. Both create bookings

Result:

❌ Invalid Data

With locking:

1. Request A acquires lock
2. Request B waits
3. Request A validates and commits
4. Request B re-checks latest data
5. Conflict detected
6. Request B rejected

Result:

✅ Consistent Data

---

## Exception Handling

Global exception handling is implemented using:

```java
@RestControllerAdvice
```

Supported exceptions:

* DedupeException
* ConflictException
* InvalidUserException
* InvalidCourseException
* InvalidOfferingException
* InvalidSessionException
* InvalidUserRoleException
* BadRequestException

Example response:

```json
{
  "status": 400,
  "message": "Invalid timezone"
}
```

---

## Assumptions

* Parent identified by Name + Mobile
* Teacher identified by Name + Mobile
* Session timings stored in UTC
* Booking occurs at Offering level
* Offering availability requires at least one future session
* Parent cannot book the same offering twice
* Parent cannot book overlapping offerings

---

## Future Enhancements

* Authentication & Authorization
* Pagination
* Course Management APIs
* Offering Capacity Limits
* Booking Cancellation
* Audit Logging
* OpenAPI / Swagger Documentation
* Docker Deployment
* CI/CD Pipeline
* Redis Caching
* Event Driven Notifications

---

## Project Highlights

<img width="1536" height="1024" alt="HighLights" src="https://github.com/user-attachments/assets/82d6558d-7d04-4a56-b0e4-16e4128aefaf" />


* UTC Time Storage
* Transactional Booking Workflow
* Pessimistic Locking
* Race Condition Prevention
* Session Conflict Detection
* Clean Domain Modeling
