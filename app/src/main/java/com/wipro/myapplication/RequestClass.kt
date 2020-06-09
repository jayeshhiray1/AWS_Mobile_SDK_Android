package com.wipro.myapplication


class RequestClass {
    lateinit var firstName: String
    lateinit var lastName: String

    constructor(firstName: String, lastName: String) {
        this.firstName = firstName
        this.lastName = lastName
    }

    constructor() {}
}