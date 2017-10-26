import {InjectionToken} from '@angular/core';
import {LANG_FR_NAME, LANG_FR_TRANS} from './lang-fr';
import {LANG_EN_NAME, LANG_EN_TRANS} from './lang-en';

export const TRANSLATIONS = new InjectionToken('translations');

export const dictionnary = {
  [LANG_FR_NAME] : LANG_FR_TRANS,
  [LANG_EN_NAME] : LANG_EN_TRANS,
}

export const TRANSLATION_PROVIDERS = [
  {provide : TRANSLATIONS, useValue: dictionnary},
];
