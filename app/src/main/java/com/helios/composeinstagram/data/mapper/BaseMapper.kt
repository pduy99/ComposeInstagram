package com.helios.composeinstagram.data.mapper

interface BaseMapper<M, E> {

    fun toEntity(model: M): E

    fun toModel(entity: E): M
}