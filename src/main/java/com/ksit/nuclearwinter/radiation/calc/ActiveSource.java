package com.ksit.nuclearwinter.radiation.calc;

import com.ksit.nuclearwinter.radiation.api.IRadiation;

// === ВСПОМОГАТЕЛЬНЫЙ КЛАСС ActiveSource =======================================
// Нужен калькулятору - позиция источника в чанках + его свойства
public record ActiveSource(int chunkX, int chunkZ, IRadiation radiation) {
}
