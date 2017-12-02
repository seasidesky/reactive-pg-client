/*
 * Copyright (C) 2017 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.julienviet.pgclient.impl;

import com.julienviet.pgclient.PgResult;
import com.julienviet.pgclient.PgRow;
import com.julienviet.pgclient.PgRowIterator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class PgResultImpl implements PgResult {

  final int updated;
  final JsonPgRow rows;
  final int size;

  public PgResultImpl(int updated) {
    this.updated = updated;
    this.rows = null;
    this.size = 0;
  }

  public PgResultImpl(JsonPgRow rows, int size) {
    this.updated = 0;
    this.rows = rows;
    this.size = size;
  }

  @Override
  public int getUpdated() {
    return updated;
  }

  @Override
  public int getNumRows() {
    return size;
  }

  @Override
  public PgRowIterator rows() {
    return new PgRowIterator() {
      JsonPgRow current = rows;
      @Override
      public boolean hasNext() {
        return current != null;
      }
      @Override
      public PgRow next() {
        if (current == null) {
          throw new NoSuchElementException();
        }
        JsonPgRow r = current;
        current = current.next;
        return r;
      }
    };
  }
}