#!/usr/bin/env node
import { loadFile } from 'nbb';
await loadFile(new URL('../src/app/core.cljs', import.meta.url).pathname);
