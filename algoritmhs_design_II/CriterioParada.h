/* 
 * File:   CriterioParada.h
 * Author: federico
 *
 */

#ifndef CRITERIOPARADA_H
#define	CRITERIOPARADA_H
#include "Pattern.h"

class CriterioParada {
public:
    CriterioParada(const CriterioParada& orig);
    virtual int parar(int,Pattern);
    virtual ~CriterioParada();
private:

};

#endif	/* CRITERIOPARADA_H */

