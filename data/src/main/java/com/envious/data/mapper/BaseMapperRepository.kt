package com.envious.data.mapper

interface BaseMapperRepository<E, D> {

    fun transform(type: E): D

    fun transformToRepository(type: D): E
}

interface BaseMapperListRepository<E : Any, D : Any> {

    fun transform(type: E): D

    fun transformToRepository(type: D): E
}
