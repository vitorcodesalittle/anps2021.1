package model.users.forms

import model.store.forms.StoreData

case class SignUpData(name: String, email: String, password: String, storeData: StoreData)

