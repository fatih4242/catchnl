//
//  User.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import Foundation

struct User: Hashable, Codable {
    var id: String?
    var name: String?
    var email: String?
    var password: String?
    var licencePlate: String?
    var licenceFile: String?
    var isAccepted: String?
    var complaintPerMonthLeft: String?
    
    var error: Int?
    var message: String?
 
}

