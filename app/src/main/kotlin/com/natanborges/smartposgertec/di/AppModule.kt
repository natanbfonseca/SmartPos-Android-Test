package com.natanborges.smartposgertec.di

import com.natanborges.smartposgertec.data.DisplayTestRepositoryImpl
import com.natanborges.smartposgertec.domain.repository.DisplayTestRepository
import com.natanborges.smartposgertec.domain.usecase.ObserveTestStateUseCase
import com.natanborges.smartposgertec.domain.usecase.ObserveTestStateUseCaseImpl
import com.natanborges.smartposgertec.domain.usecase.StartTestUseCase
import com.natanborges.smartposgertec.domain.usecase.StartTestUseCaseImpl
import com.natanborges.smartposgertec.domain.usecase.TouchCellUseCase
import com.natanborges.smartposgertec.domain.usecase.TouchCellUseCaseImpl
import com.natanborges.smartposgertec.presentation.test.TestViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::TestViewModel)

    factoryOf(::ObserveTestStateUseCaseImpl).bind<ObserveTestStateUseCase>()
    factoryOf(::StartTestUseCaseImpl).bind<StartTestUseCase>()
    factoryOf(::TouchCellUseCaseImpl).bind<TouchCellUseCase>()

    singleOf(::DisplayTestRepositoryImpl).bind<DisplayTestRepository>()
}