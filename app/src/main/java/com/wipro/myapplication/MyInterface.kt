package com.wipro.myapplication

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction

interface MyInterface {

    /**
     * Invoke the Lambda function "AndroidBackendLambdaFunction".
     * The function name is the method name.
     */
    @LambdaFunction
    fun AndroidBackendLambdaFunction(request: RequestClass): ResponseClass

}