package model.services.session

// Used to type the json encoded in JWT tokens produced by SessionService
case class UserInfo(id: Int, storeId: Int)
