Model

[x] User

    private int userId;
    private String bankingAccount;
    private String identification;
    private String preferences;

    constructor
        public User(field variables)
    
    getters/setters

[x] Rider (Seat)

    int riderId
    BigDecimal totalCost
    boolean paymentConfirmation

    constructor
        public Rider(field variables)

    getters/setters

[x] Car (Driver)

    int carId
    String(dropdown) / boolean(?) insurance
    String (dropdown) / boolean(?) registration
    String make
    String model
    String year
    String color
    String licensePlate

    constructor
        public Car(field variables)
    
    getters/setters

[x] Trip
    
    int tripId
    String departure
    String arrival 
    LocalDate date

    constructor
        public Trip(field variables)
    
    getters/setters

[ ] Rating

    int ratingId
    int authorId(?)
    int subjectId(?)
    int rating
    String description

    constructor
        public Rating(field variables)
    
    getters/setters

=======================================

Controller

[x] GlobalExceptionHandler

    add @ControllerAdvice
    add @ExceptionHandler
        DataIntegrityViolationException.class
        Exception.class

[x] ErrorResponse

    reference field agent ErrorResponse class

[ ] UnregisteredUserController

    add @RestController
    add @CrossOrigin(origins = {"http://localhost:3000"})
    add @RequestMapping("/api/home")

    field variables
        private final UnregistedUserService service

    constructor
        public UnregisteredUserController(UnregisteredUserService service) 

    @GetMapping
    public List<Trip> findAll()
     
    @PostMapping
    public ResponseEntity<Object> createAccount(@RequestBody User user)

[ ] RegisteredUserController
    
    add @RestController
    add @CrossOrigin(origins = {"http://localhost:3000"})
    add @RequestMapping("/api/registered-user")

    field variables
        private final RegisteredUserService service

    construtor
        public RegisteredUserController(RegisteredUserService service)

    @GetMapping("/{userId}")
    public Account findByUserId(@PathVariable int userId)

    @PostMapping()
    public ResponseEntity<Object> createCar(@RequestBody Car car)

[ ] CarController

    add @RestController
    add @CrossOrigin(origins = {"http://localhost:3000"})
    add @RequestMapping("/api/car")

    field variables 
        private final CarService service;

    constructor
        public CarController(CarService service)

    @GetMapping("/{tripId}")
    public Trip findByTripId(@PathVariable in tripId)

    @PostMapping()
    public ResponseEntity<Object> createTrip(@RequestBody Trip trip)
    

[ ] RiderController/ trip controller??

    add @RestController
    add @CrossOrigin(origins = {"http://localhost:3000"})
    add @RequestMapping("/api/rider")

    field variables 
        private final RiderService service;

    constructor
        public RiderController(RiderService service)

    @GetMapping("/userId}")
    public List<Trip> findAll(@PathVariable int userId)

    @GetMapping("/{tripId}")
    public Trip findByTripId(@PathVariable int tripId)

    @PutMapping("/{tripId}")
    public ResponseEntity<Object> update(@PathVariable int tripId, @RequestBody RegisteredUser registeredUser)

======================================================

Domain

[ ] UnregisteredService
    
    field variables
        private final TripRepository repository
    
    constructor
        public UnregisteredService(TripRepository repository)

    public List<Trip> findAll()
        return repository.findAll();

    public Result<User> createAccount(User user)
        Result<User> result = validate(user)
        if (!result.isSuccess()) {
            return result
        }

        check if userId != 0
            result.addMessage
            return result

        user = repository.add(user)
        result.setPayload(user)
        return result

    private Result<User> valaidate(User user)
        Result<User> result = new Result<>()
        
        check if the user is null
        
        Validations.isNullOrBlank
            1) bankingAccount
            2) identifications
            3) userId (YAGNI??)

[ ] RegisteredService

    field variables
        private final CarRepository carRepository
        private final UserRepsitory userRepository
    
    constructor
        public RegisteredService(CarRepository carRepository, UserRepostory userRepository)

    public User findByUserId(int userId)
        return userRepository.findByUserId(userId)

    public Result<Car> createCar(Car car)
        Result<Car> result = validate(car0
        if not sucess, return result

        if (car.getCarId() != 0)
            result.addMessage and return result

        car = carRepository.create(car)
        result.setPayload(car)
        return result

    private Result<Car> validate(Car car)
        Result<Car> result = new Result<>()
        
        if (car == null)
            user must own a car to become a driver DUH?

        Validations
            insurance
            registeration     
            make
            model
            color
            year (optional)
            license plate

[ ] CarService
    
    field variables
        private final TripRepository repo
    
    constructors
        public CarService(TripRepository repo)

    public Trip findByTripId(int TripId tripId)
        return repo.findByTripId(tripId)

    public Result<Trip> createTrip(Trip trip)
        Result<Trip> result = validate(trip)

        check !result.isSuccess
            return result

        if (trip.getTripId() != 0)
            result.addMessage
            return result

        trip = repo.add(trip)
        result.setPayload(trip)
        return result

    private Result<Trip> valiadate(Trip trip)

        Validate
            Departure
            Arrival
            Date

[ ] RiderService

    field variables
        private final TripRepository repo

    constructors
        public RiderService(TripRepository repo)

    //public ResponseEntity<Object> update(@PathVariable int tripId, @RequestBody RegisteredUser registeredUser)
    
    public List<Trip> findAll()
        return repo findAll

    public Trip findByTripId(int tripId)
        return repo findByTripId

    public Result<Trip> join(Trip trip)
        Result<Trip> result = validate(trip)
        
        check !result.isSuccess
            return result

        if id <= 0
            result.addMessage (no trip found)
            return result

        if !repo.update(trip) 
            tripId not found
            result.addMessage

        return result;

    private Result<Trip> validate(Trip trip)

        Valiations
            if total cost less than or equal 0
                result.addMessage(total cost cannot be less than or equal to 0)

            if confirmation of the payment != true
                result.addMessage(payment is not confirmed)

        return result

[x] Result

    private final ArrayList<String> messages = new ArrayList<>();
    private ResultType type = ResultType.SUCCESS;
    private T payload;

    public ResultType getType() {
        return type;
    }

    public boolean isSuccess() {
        return type == ResultType.SUCCESS;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage(String message, ResultType type) {
        messages.add(message);
        this.type = type;
    }

[x] ActionStatus

    public enum ResultType {
        SUCCESS,
        INVALID,
        NOT_FOUND
    }

[x] Validations

      public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

================================================================

Data

[x] TripJdbcTemplateRepository
    
    field variables
        private final JdbcTemplate jdbcTemplate

    public TripJdbcTemplateRepository(JdbcTemplate jdbcTemplate)

    @Override
    findAll()

    @Override
    findByTripId()

    @Override
    createTrip

    @Override
    updateTrip

    @Override
    @Transactional
    deleteTrip

[x] TripRepository

    extract from JdbcTemplateRepo

[x] UserJdbcTemplateRepository

    field variables
        private final JdbcTemplate jdbcTemplate

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate)

    @Override
    findByUserId()

    @Override
    createUser()

    @Override
    updateByUserId()

    @Override
    @Transactional // delete from Rider and Car first maybe??
    deleteByUserId()

[x] UserRepository

    extract from JdbcTemplateRepo

[x] CarJdbcTemplateRepository

    field variables
        private final JdbcTemplate jdbcTemplate

    public CarJdbcTemplateRepository(JdbcTemplate jdbcTemplate)

    @Override
    createCar()

    @Override
    findByCarId()

    @Override
    updateByCarId()

    @Override
    @Transactional (delete from Trip first(?))
    deleteByCarId()

[x] CarRepository

    extract from JdbcTemplateRepo

=================================================================

Mapper

[x] TripMapper

[x] UserMapper

[x] RiderMapper

[x] CarMapper

[ ] RatingMapper

=================================================================

Security

https://github.com/dev10-program/cohort-39/tree/main/M10/lms

[x] Spring Token-based Security
    
    follow the lesson

[x] Database-driven Security

    follow the lesson

[x] React Security

    follow the lesson

=================================================================
