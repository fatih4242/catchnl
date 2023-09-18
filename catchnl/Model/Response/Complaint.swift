//
//  Complaint.swift
//  catchnl
//
//  Created by Fatih Toker on 15.09.2023.
//

import Foundation

struct Complaint : Hashable, Codable {
    var id : String
    var licencePlate : String
    var date : String
    var incident : String
    var userID : String
    var place : String
}
